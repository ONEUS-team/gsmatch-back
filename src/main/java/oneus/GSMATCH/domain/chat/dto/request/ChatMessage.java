package oneus.GSMATCH.domain.chat.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {
    private String message;
    private String token;
}
