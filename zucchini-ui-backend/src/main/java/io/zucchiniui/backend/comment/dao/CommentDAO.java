package io.zucchiniui.backend.comment.dao;

import io.zucchiniui.backend.comment.domain.Comment;
import io.zucchiniui.backend.comment.domain.CommentQuery;
import io.zucchiniui.backend.support.ddd.morphia.MorphiaTypedQueryDAO;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.function.Consumer;

@Singleton
public class CommentDAO extends MorphiaTypedQueryDAO<Comment, String, CommentQuery> {

    @Inject
    public CommentDAO(final Datastore ds) {
        super(ds);
    }

    @Override
    public Query<Comment> prepareTypedQuery(final Consumer<? super CommentQuery> preparator) {
        final CommentQueryImpl typedQuery = new CommentQueryImpl(createQuery());
        preparator.accept(typedQuery);
        return typedQuery.morphiaQuery();
    }

}
