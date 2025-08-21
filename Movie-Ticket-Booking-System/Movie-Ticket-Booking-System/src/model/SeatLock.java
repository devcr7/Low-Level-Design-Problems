package model;

import java.time.Instant;
import java.util.Date;

public class SeatLock {
    private final Seat seat;
    private final Show show;
    private final User lockedBy;
    private final Integer timeoutInSeconds;
    private final Date lockTime;

    public SeatLock(Seat seat, Show show, User lockedBy, Integer timeoutInSeconds, Date lockTime) {
        this.seat = seat;
        this.show = show;
        this.lockedBy = lockedBy;
        this.timeoutInSeconds = timeoutInSeconds;
        this.lockTime = lockTime;
    }

    public boolean isLockExpired() {
        final Instant lockInstant = lockTime.toInstant().plusSeconds(timeoutInSeconds);
        return Instant.now().isAfter(lockInstant);
    }

    public Seat getSeat() {
        return seat;
    }

    public Show getShow() {
        return show;
    }

    public User getLockedBy() {
        return lockedBy;
    }
}
