package com.example.bookingapp.repository.mongodb;

import com.example.bookingapp.entity.mongodb.SequenceId;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoExpression;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
@Slf4j
public class SequenceDAO {

    @Autowired
    private MongoOperations mongoOperations;

    public Long getNewSequence(String kye) {
        Query query = new Query(Criteria.where("_id").is(kye));
        Update update = new Update();
        update.inc("seq", 1L);
        FindAndModifyOptions options = new FindAndModifyOptions();
        options.returnNew(true);
        SequenceId newSequence = mongoOperations.findAndModify(query, update, SequenceId.class);
        return Objects.isNull(newSequence) ? 1L : newSequence.getSeq();
    }

    @PostConstruct
    public void createEntity() {
        List<SequenceId> exestEntity = mongoOperations.findAll(SequenceId.class);
        if (exestEntity == null) {
            SequenceId bookingRoomInformationSequenceId = new SequenceId();
            bookingRoomInformationSequenceId.setId("booking");
            bookingRoomInformationSequenceId.setSeq(0L);
            SequenceId registrationUserInformationSequenceId = new SequenceId();
            registrationUserInformationSequenceId.setId("regUser");
            registrationUserInformationSequenceId.setSeq(0L);
            mongoOperations.insert(bookingRoomInformationSequenceId);
            mongoOperations.insert(registrationUserInformationSequenceId);
        }
    }

}
