package com.example.bookingapp.entity.mongodb;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "sequenceId")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SequenceId {

    @Id
    private String id;

    private Long seq;

}
