package com.yaonie.intelligent.assessment.server.chat_server.user.entity.enums;


import lombok.Getter;

/**
 * @author 77160
 */

@Getter
public enum PageSize {
	SIZE15(15), SIZE20(20), SIZE30(30), SIZE40(40), SIZE50(50);
	final int size;

	private PageSize(int size) {
		this.size = size;
	}

}
