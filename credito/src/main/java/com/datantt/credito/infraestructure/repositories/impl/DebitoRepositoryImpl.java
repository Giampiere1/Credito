package com.datantt.credito.infraestructure.repositories.impl;

import com.datantt.credito.domain.dtos.CustomerDTO;
import com.datantt.credito.domain.dtos.DebitoDto;
import com.datantt.credito.infraestructure.repositories.DebitoRepository;
import com.mongodb.client.*;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class DebitoRepositoryImpl implements DebitoRepository {

    @Value("${connectionstring}")
    private String connectionString;

    @Value("${dbname}")
    private String dbname;

    @Value("${debitocollection}")
    private String debitocollection;

    @Value("${paymentcollection}")
    private String paymentcollection;
    @Override
    public List<Document> getList(CustomerDTO customerDTO) {
        System.out.println("CustomerDTO Id");
        System.out.println(customerDTO.get_id());
        List<Document> result = new ArrayList<>();
        MongoClient client = MongoClients.create(connectionString);
        MongoDatabase database = client.getDatabase(dbname);
        MongoCollection<Document> debitos = database.getCollection(debitocollection);
        FindIterable<Document> documents = debitos
                .find(new Document("Customer_Id", customerDTO.get_id()));
        for (Document document : documents) {
            result.add(document);
        }
        return result;
    }

    @Override
    public Boolean create(DebitoDto debitoDto) {
        MongoClient client = MongoClients.create(connectionString);
        MongoDatabase database = client.getDatabase(dbname);
        MongoCollection<Document> collection = database.getCollection(debitocollection);
        Document document = new Document("TypeCode", debitoDto.getTypeCode())
                .append("Customer_Id", debitoDto.getCustomer_Id())
                .append("Amount", debitoDto.getAmount())
                .append("Consumed", 0)
                .append("HasCard", debitoDto.getHasCard())
                .append("CardNumber", debitoDto.getCardNumber())
                .append("Estado", debitoDto.getEstado())
                .append("StarDate", debitoDto.getStartDate())
                .append("FinDate", debitoDto.getEndDate());
        collection.insertOne(document);
        System.out.println("DOCUMENTO CREADO");
        System.out.println(document.get("_id").toString());
        return true;
    }


}
