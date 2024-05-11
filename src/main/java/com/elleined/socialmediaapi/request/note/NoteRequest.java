package com.elleined.socialmediaapi.request.note;

import com.elleined.socialmediaapi.request.Request;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class NoteRequest extends Request {
    private String thought;
    private int creator;

    @Builder
    public NoteRequest(String thought, int creator) {
        this.thought = thought;
        this.creator = creator;
    }
}
