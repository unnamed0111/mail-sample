package study.gonet.mailsample;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ContentRequestDTO {
    private List<ContentDTO> contentList;
    private String email;
}
