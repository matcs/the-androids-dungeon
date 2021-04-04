package com.matcss.androidsdungeon.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.matcss.androidsdungeon.interfaces.Images;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Page implements Images {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int pageId;

    @Column(length = 1000)
    private byte[] pageImage;

    private int pageNumber;

    @ManyToOne
    @JoinColumn(nullable = false)
    @JsonIgnore
    private Book book;

    public Page(int pageId, byte[] pageImage, int pageNumber) {
        this.pageId = pageId;
        this.pageImage = pageImage;
        this.pageNumber = pageNumber;
    }

    public Page(byte[] pageImage, int pageNumber) {
        this.pageImage = pageImage;
        this.pageNumber = pageNumber;
    }

    public Page(int pageNumber, byte[] pageImage, Book book) {
        this.pageNumber = pageNumber;
        this.pageImage = pageImage;
        this.book = book;
    }

    public Page(int pageId, byte[] pageImage, int pageNumber, Book book) {
        this.pageId = pageId;
        this.pageImage = pageImage;
        this.pageNumber = pageNumber;
        this.book = book;
    }

    @Override
    public byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return outputStream.toByteArray();
    }

    @Override
    public byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        } catch (DataFormatException e) {
            System.out.println(e.getMessage());
        }

        return outputStream.toByteArray();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Page page = (Page) o;
        return pageId == page.pageId && pageNumber == page.pageNumber && Arrays.equals(pageImage, page.pageImage);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(pageId, pageNumber);
        result = 31 * result + Arrays.hashCode(pageImage);
        return result;
    }

    @Override
    public String toString() {
        return "Page{" +
                "pageId=" + pageId +
                ", pageImage=" + Arrays.toString(pageImage) +
                ", pageNumber=" + pageNumber +
                ", book=" + book +
                '}';
    }
}
