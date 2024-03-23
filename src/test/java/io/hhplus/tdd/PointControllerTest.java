package io.hhplus.tdd;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.*;

import io.hhplus.tdd.database.UserPointTable;
import io.hhplus.tdd.point.controller.PointController;
import io.hhplus.tdd.point.domain.UserPoint;
import io.hhplus.tdd.point.service.UserPointService;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;



@WebMvcTest(controllers = PointController.class)
public class PointControllerTest {

	@Autowired
    MockMvc mockMvc;
	@MockBean
    private UserPointService userPointService;
	@MockBean
	private UserPointTable userPointTable;
	


    @Test
    @DisplayName("포인트 조회 컨트롤러 테스트")
    void point() throws Exception {
    	

    	long id = 1L;
    	UserPoint userPoint = new UserPoint(id, 100, System.currentTimeMillis());
    	given(userPointService.getUserPoint(id)).willReturn(userPoint);
    	
    	mockMvc.perform(
                MockMvcRequestBuilders.get("/point/"+id))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").exists()) ;
    
    	
    }
	
	
	

}
