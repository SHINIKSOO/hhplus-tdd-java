package io.hhplus.tdd.point.service;

import org.springframework.stereotype.Service;

import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.database.UserPointTable;
import io.hhplus.tdd.point.domain.TransactionType;
import io.hhplus.tdd.point.domain.UserPoint;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserPointService {

	private final PointHistoryTable pointHistoryTable;
	private final UserPointTable userPointTable;
	
	/**
     * 특정 유저의 포인트를 충전한다.
     */
	public UserPoint charge(Long id, Long chargePoint) {
	    UserPoint userPoint = userPointTable.insertOrUpdate(id, chargePoint+userPointTable.selectById(id).point());
	    pointHistoryTable.insert(id, chargePoint, TransactionType.CHARGE, userPoint.updateMillis());
	    return userPoint;
	  }

}
