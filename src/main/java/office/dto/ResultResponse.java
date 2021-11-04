package office.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResultResponse {

    //응답 코드
    private int code;

    //응답 메세지
    private String msg;
}