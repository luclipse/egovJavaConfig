package org.egovframe.cmm.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.egovframe.cmm.code.ResultStatus;

@SuperBuilder
@Getter @Setter
public class ResultVO {

	private String resultCode;
	private String resultMessage;
	private Object result;
	private ResultStatus status;

	void setRsultSatus(ResultStatus status){
		setResultCode(status.code());
		setResultMessage(status.message());
		setStatus(status);
	}
}
