package service.impl;

import model.Seat;
import model.SeatLock;
import model.Show;
import model.User;
import service.ISeatLockProvider;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class SeatLockProvider implements ISeatLockProvider {
    private final Integer lockTimeout;
    private final Map<Show, Map<Seat, SeatLock>> locks;

    public SeatLockProvider(Integer lockTimeout) {
        this.lockTimeout = lockTimeout;
        this.locks = new ConcurrentHashMap<>();
    }


    @Override
    public void lockSeats(Show show, List<Seat> seats, User user) throws Exception {
        Map<Seat, SeatLock> seatLocks = locks.computeIfAbsent(show, s -> new ConcurrentHashMap<>());

        synchronized (seatLocks) {
            for (Seat seat: seats) {
                if (seatLocks.containsKey(seat)) {
                    SeatLock existingLock = seatLocks.get(seat);
                    if (!existingLock.isLockExpired()) {
                        throw new Exception("Seat " + seat.getId() + " is already locked by " + existingLock.getLockedBy().getName());
                    }
                }
            }

            for (Seat seat: seats) {
                SeatLock newLock = new SeatLock(seat, show, user, lockTimeout, new Date());
                seatLocks.put(seat, newLock);
            }
        }
    }

    @Override
    public void unlockSeats(Show show, List<Seat> seats, User user) {
        Map<Seat, SeatLock> seatLocks = locks.get(show);
        if (seatLocks == null) {
            return;
        }

        synchronized (seatLocks) {
            for (Seat seat: seats) {
                SeatLock existingLock = seatLocks.get(show);
                if (existingLock != null && existingLock.getLockedBy().equals(user)) {
                    seatLocks.remove(seat);
                }
            }
        }
    }

    @Override
    public boolean validateSeatLock(Show show, Seat seat, User user) {
        Map<Seat, SeatLock> seatLocks = locks.get(show);

        if (seatLocks == null) {
            return false;
        }

        synchronized (seatLocks) {
            SeatLock lock = seatLocks.get(seat);
            return lock != null && !lock.isLockExpired() && lock.getLockedBy().equals(user);
        }
    }

    @Override
    public List<Seat> getLockedSeats(Show show) {
        Map<Seat, SeatLock> seatLocks = locks.get(show);
        if (seatLocks == null) {
            return Collections.emptyList();
        }

        synchronized (seatLocks) {
            return seatLocks.entrySet()
                    .stream()
                    .filter(entry -> !entry.getValue().isLockExpired())
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());
        }
    }
}
