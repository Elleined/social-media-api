package com.elleined.socialmediaapi.request.note;

import com.elleined.socialmediaapi.request.Request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class NoteRequest extends Request {

    @NotBlank(message = "Please provide your thought")
    private String thought;

    @Positive(message = "Please provide creator id")
    private int creatorId;

    @Builder
    public NoteRequest(String thought,
                       int creatorId) {
        this.thought = thought;
        this.creatorId = creatorId;
    }
}
