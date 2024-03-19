package io.hhplus.tdd.point.service;

import java.util.List;

import org.springframework.stereotype.Service;

import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.database.UserPointTable;
import io.hhplus.tdd.exception.ErrorException;
import io.hhplus.tdd.point.domain.PointHistory;
import io.hhplus.tdd.point.domain.TransactionType;
import io.hhplus.tdd.point.domain.UserPoint;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserPointService {

	private final PointHistoryTable pointHistoryTable;
	private final UserPointTable userPointTable;

	/**
	 * 특정 유저의 포인트를 조회한다.
	 */
	public UserPoint getUserPoint(Long id) {
		UserPoint userPoint = userPointTable.selectById(id);
		return userPoint;
	}

	/**
	 * 특정 유저의 포인트를 충전한다.
	 */
	public UserPoint chargePoint(Long id, Long chargePoint) {
		if (chargePoint < 0) {
			throw new ErrorException();
		}
		UserPoint userPoint = userPointTable.insertOrUpdate(id, chargePoint + userPointTable.selectById(id).point());
		pointHistoryTable.insert(id, chargePoint, TransactionType.CHARGE, userPoint.updateMillis());
		return userPoint;
	}
	
	/**
	 * 특정 유저의 포인트를 사용한다.
	 */
	public UserPoint usePoint(Long id, Long usePoint) {
		UserPoint userPoint = userPointTable.selectById(id);
        UserPoint resultUserPoint = userPointTable.insertOrUpdate(userPoint.id(),userPoint.point() - usePoint);
        pointHistoryTable.insert(id, usePoint, TransactionType.USE, userPoint.updateMillis());
        return resultUserPoint;
	}
	
	/**
	 * 특정 유저의 포인트 충전/이용 내역을 조회한다.
	 */
	public List<PointHistory> getUserHistory(long id) {
        return pointHistoryTable.selectAllByUserId(id);
    }
	
	

}
