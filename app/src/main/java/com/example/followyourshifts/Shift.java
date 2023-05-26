package com.example.followyourshifts;

import java.time.LocalDate;

public class Shift  {
    private Workplace workplace;
    private LocalDate localDate;

    public Shift(Workplace workplace,LocalDate localDate) {
        this.workplace = workplace;
        this.localDate = localDate;
    }

    public Workplace getWorkplace() {
        return workplace;
    }

    public void setWorkplace(Workplace workplace) {
        this.workplace = workplace;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }
}
