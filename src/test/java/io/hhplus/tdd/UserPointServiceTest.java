package io.hhplus.tdd;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.database.UserPointTable;
import io.hhplus.tdd.point.domain.UserPoint;
import io.hhplus.tdd.point.service.UserPointService;

public class UserPointServiceTest {
	
	private UserPointService pointService;
	@BeforeEach
    void setUp() {
		pointService = new UserPointService(new PointHistoryTable(), new UserPointTable());
    }
	
	@Test
	@DisplayName("해당 유저의 포인트를 충전한다.")
	void chargeTest() {
		// given
		UserPoint userPoint = pointService.charge(1L, 100L);
		Long userId = userPoint.id();
		Long chargePoint = 500L;
		// when
		UserPoint chargedUserPoint = pointService.charge(userId, chargePoint);
		// then
		assertThat(userPoint.id()).isEqualTo(chargedUserPoint.id());
		assertThat(chargedUserPoint.point()).isEqualTo(600L);
	}

}
