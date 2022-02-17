package com.example.databases;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.pojo.Quotation;

import java.util.List;

@Dao
public interface QuotationDAO {

    @Insert
    public void addQuotation(Quotation quotation);

    @Delete
    public void deleteQuotation(Quotation quotation);

    @Query("SELECT * FROM quotation")
    public List<Quotation> obtainAllQuotation();

    @Query("SELECT * FROM quotation WHERE quoteText = :quote")
    public Quotation obtainQuotation(String quote);

    @Query("DELETE FROM quotation")
    public void deleteAllQuotation();

}
