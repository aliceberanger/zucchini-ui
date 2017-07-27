package io.zucchiniui.backend.comment.domainimpl;

import dagger.Binds;
import dagger.Module;
import io.zucchiniui.backend.comment.domain.CommentRepository;

@Module
public abstract class CommentDomainModule {

    @Binds
    public abstract CommentRepository commentRepository(CommentRepositoryImpl commentRepositoryImpl);

}
