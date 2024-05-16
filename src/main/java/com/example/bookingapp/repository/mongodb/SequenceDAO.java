package com.example.bookingapp.repository.mongodb;

import com.example.bookingapp.entity.mongodb.SequenceId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
public class SequenceDAO {

    @Autowired
    private MongoOperations mongoOperations;


    public Long getNewSequence(String kye){

        Query query = new Query(Criteria.where("_id").is(kye));

        Update update = new Update();
        update.inc("seq",1);

        FindAndModifyOptions options = new FindAndModifyOptions();
        options.returnNew(true);

        SequenceId newSequence = mongoOperations.findAndModify(query,update, SequenceId.class);

        return Objects.isNull(newSequence) ? 1L : newSequence.getSeq();

    }
}
