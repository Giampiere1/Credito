package com.datantt.credito.infraestructure.repositories.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.datantt.credito.domain.dtos.CreditDTO;
import com.datantt.credito.domain.dtos.CustomerDTO;
import com.datantt.credito.domain.dtos.PaymentDTO;
import com.datantt.credito.domain.util.Constantes;
import com.datantt.credito.domain.util.Util;
import com.datantt.credito.infraestructure.repositories.CreditRepository;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@Repository
public class CreditRepositoryImpl implements CreditRepository {

    @Value("${connectionstring}")
    private String connectionString;

    @Value("${dbname}")
    private String dbname;

    @Value("${creditcollection}")
    private String creditcollection;

    @Value("${paymentcollection}")
    private String paymentcollection;

    @Override
    public List<Document> getList(CustomerDTO customerDTO) {
        System.out.println("CustomerDTO Id");
        System.out.println(customerDTO.get_id());
        List<Document> result = new ArrayList<>();
        MongoClient client = MongoClients.create(connectionString);
        MongoDatabase database = client.getDatabase(dbname);
        MongoCollection<Document> creditos = database.getCollection(creditcollection);
        FindIterable<Document> documents = creditos
                .find(new Document("Customer_Id", customerDTO.get_id()));
        for (Document document : documents) {
            result.add(document);
        }
        return result;
    }

    @Override
    public Document getDetail(String id) {
        MongoClient client = MongoClients.create(connectionString);
        MongoDatabase database = client.getDatabase(dbname);
        MongoCollection<Document> collection = database.getCollection(creditcollection);
        return collection.find(new Document("_id", new ObjectId(id))).first();
    }

    @Override
    public Boolean create(CreditDTO creditDTO) {
        MongoClient client = MongoClients.create(connectionString);
        MongoDatabase database = client.getDatabase(dbname);
        MongoCollection<Document> collection = database.getCollection(creditcollection);
        Document document = new Document("TypeCode", creditDTO.getTypeCode())
                .append("Customer_Id", creditDTO.getCustomer_Id())
                .append("Amount", creditDTO.getAmount())
                .append("Consumed", 0)
                .append("HasCard", creditDTO.getHasCard())
                .append("CardNumber", creditDTO.getCardNumber())
                .append("Estado", creditDTO.getEstado())
                .append("StarDate", creditDTO.getStartDate())
                .append("FinDate", creditDTO.getEndDate());
        collection.insertOne(document);
        System.out.println("DOCUMENTO CREADO");
        System.out.println(document.get("_id").toString());
        return true;
    }

    @Override
    public List<Document> getListPayment(CreditDTO creditDTO) {
        System.out.println("CreditDTO Id");
        System.out.println(creditDTO.get_id());
        List<Document> result = new ArrayList<>();
        MongoClient client = MongoClients.create(connectionString);
        MongoDatabase database = client.getDatabase(dbname);
        MongoCollection<Document> transacciones = database.getCollection(paymentcollection);
        FindIterable<Document> documents = transacciones
                .find(new Document("Credit_Id", new ObjectId(creditDTO.get_id())));
        for (Document document : documents) {
            result.add(document);
        }
        return result;
    }

    @Override
    public Boolean createPayment(PaymentDTO paymentDTO) {
        MongoClient client = MongoClients.create(connectionString);
        MongoDatabase database = client.getDatabase(dbname);
        MongoCollection<Document> collection = database.getCollection(paymentcollection);
        Document document = new Document("Credit_Id", new ObjectId(paymentDTO.getCredit_Id()))
                .append("TypeCode", paymentDTO.getTypeCode())
                .append("Amount", paymentDTO.getAmount());
        collection.insertOne(document);
        collection = database.getCollection(creditcollection);
        document = getDetail(paymentDTO.getCredit_Id());
        CreditDTO credit = Util.mapTo(document, CreditDTO.class);
        System.out.println("MONTO antes");
        System.out.println(credit.getAmount());
        System.out.println("CONSUMO antes");
        System.out.println(credit.getConsumed());
        Document replace = new Document("$set",
                new Document("Consumed", Double.parseDouble(credit.getConsumed()) + Double
                        .parseDouble(paymentDTO.getAmount())
                        * (paymentDTO.getTypeCode().equalsIgnoreCase(Constantes.TIPOPAGO_CONSUMO) ? 1
                                : -1)));
        collection.updateOne(new Document("_id", new ObjectId(paymentDTO.getCredit_Id())), replace);
        return true;
    }

}
