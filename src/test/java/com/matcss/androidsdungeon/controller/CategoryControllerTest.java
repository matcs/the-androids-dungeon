package com.matcss.androidsdungeon.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matcss.androidsdungeon.model.Category;
import com.matcss.androidsdungeon.service.CategoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {CategoryController.class, CategoryService.class})
@WebMvcTest(CategoryController.class)
@AutoConfigureMockMvc
public class CategoryControllerTest {

    private static final String urlTemplate = "/category";
    private final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Test
    public void givenAllCategories_whenGetCategories_thenReturnHttpStatus200() throws Exception{
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1,"MANGA"));
        categories.add(new Category(1,"GRAPHIC_NOVEL"));
        categories.add(new Category(1,"COMIC"));

        given(categoryService.findAll()).willReturn(categories);

        mockMvc
                .perform(MockMvcRequestBuilders.get(urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].categoryId").value(categories.get(0).getCategoryId()));
    }

    @Test
    public void createCategory_And_ShowHisJson() throws Exception {
        Category category = new Category(1,"MANGA");

        when(categoryService.create(category)).thenReturn(category);

        mockMvc
                .perform(MockMvcRequestBuilders.post(urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(category)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.categoryId").value(category.getCategoryId()));
    }

    @Test
    public void updateCategory_And_ReturnUpdatedCategory_With_AcceptedHttpStatus() throws Exception {
        final int categoryId = 1;

        Category foundCategory = new Category(categoryId,"MAMÃO");
        Category categoryBody = new Category("MANGA");
        Category updatedCategory = new Category(categoryId,"MANGA");

        when(categoryService.findById(categoryId)).thenReturn(foundCategory);
        when(categoryService.update(categoryId, categoryBody)).thenReturn(updatedCategory);

        mockMvc
                .perform(MockMvcRequestBuilders.put(urlTemplate+"/{categoryId}",categoryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(categoryBody)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.categoryId").value(foundCategory.getCategoryId()))
                .andDo(print());
    }



}
