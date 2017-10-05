package net.avdw.typing;

import java.util.function.Supplier;

class NumberDelta<C> {

    final Supplier<Double> watch;
    Double value = Double.NaN;
    Double last = Double.NaN;
    Double delta = Double.NaN;

    NumberDelta(Supplier<Double> watch) {
        this.watch = watch;
    }

    void update() {
        this.last = this.value;
        this.value = this.watch.get();
        this.delta = this.value - this.last;
    }

    @Override
    public String toString() {
        return this.value + ":" + this.delta;
    }
}
