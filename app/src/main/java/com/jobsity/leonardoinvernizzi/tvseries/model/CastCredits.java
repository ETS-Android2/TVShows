package com.jobsity.leonardoinvernizzi.tvseries.model;

public class CastCredits {

    public class Embedded {
        private Show show;

        public Show getShow() {
            return show;
        }

        public void setShow(Show show) {
            this.show = show;
        }
    }

    private Embedded _embedded;

    public Embedded get_embedded() {
        return _embedded;
    }

    public void set_embedded(Embedded _embedded) {
        this._embedded = _embedded;
    }
}
