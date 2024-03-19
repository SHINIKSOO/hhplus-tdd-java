package io.hhplus.tdd;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.database.UserPointTable;
import io.hhplus.tdd.point.domain.PointHistory;
import io.hhplus.tdd.point.domain.TransactionType;
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
		UserPoint userPoint = pointService.chargePoint(1L, 100L);
		Long userId = userPoint.id();
		Long chargePoint = 500L;
		// when
		UserPoint chargedUserPoint = pointService.chargePoint(userId, chargePoint);
		// then
		assertThat(userPoint.id()).isEqualTo(chargedUserPoint.id());
		assertThat(chargedUserPoint.point()).isEqualTo(600L);
	}
	
	@Test
	@DisplayName("해당 유저의 포인트를 조회한다.")
	void getUserPointTest() {
		// given
		UserPoint newUser = pointService.chargePoint(1L, 1000L);
		// when
		Long userId = newUser.id();
		UserPoint userInfo = pointService.getUserPoint(userId);
		// then
		assertThat(userId).isEqualTo(userInfo.id());

	}
	
	@Test
	@DisplayName("해당 유저의 포인트를 사용한다.")
	void usePointTest() {
		// given
		UserPoint newUser = pointService.chargePoint(1L, 1000L);
		// when
		Long userId = newUser.id();
		Long usedPoint = 500L;
		UserPoint userInfo = pointService.usePoint(userId,usedPoint);
		// then
		assertThat(userInfo.point()).isEqualTo(newUser.point()-usedPoint);

	}
	
	@Test
	@DisplayName("특정 유저의 포인트 충전/이용 내역을 조회한다.")
	void getUserHistoryTest() {
		// given
		UserPoint newUser = pointService.chargePoint(1L, 1000L);
		// when
		Long userId = newUser.id();
		pointService.usePoint(userId,200L);
		pointService.usePoint(userId,500L);
		List<PointHistory> list = pointService.getUserHistory(userId);
		// then
		assertThat(TransactionType.CHARGE).isEqualTo(list.get(0).type());

	}
	
	

}
