package com.elleined.socialmediaapi.dto;

import com.elleined.socialmediaapi.model.user.User;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

public abstract class HateoasDTO extends RepresentationModel<HateoasDTO> {
    public HateoasDTO addLinks(User currentUser, boolean doInclude) {
        this.addAllIf(doInclude, () -> getAllSelfLinks(currentUser, doInclude));
        this.addAllIf(doInclude, () -> getAllRelatedLinks(currentUser , doInclude));

        return this;
    }

    protected abstract List<Link> getAllRelatedLinks(User currentUser, boolean doInclude);
    protected abstract List<Link> getAllSelfLinks(User currentUser, boolean doInclude);
}