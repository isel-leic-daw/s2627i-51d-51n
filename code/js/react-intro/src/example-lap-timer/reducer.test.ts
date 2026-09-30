import { describe, expect, test } from "vitest";
import {
  type State,
  actionLap,
  actionReset,
  actionStart,
  actionStop,
  actionTick,
  initialState,
  reduce,
} from "./reducer";
import { duration, instant } from "./time";

describe("lap timer reducer", () => {
  test("happy flow: start, tick, lap, tick, lap, stop, reset", () => {
    // given: the initial state
    let state: State = initialState;

    // when: the timer is started at 1000
    state = reduce(state, actionStart(instant(1000)));
    // then: no time has elapsed
    expect(state).toEqual({
      kind: "running",
      laps: [],
      startTimestamp: instant(1000),
      lastLapTimestamp: instant(1000),
      elapsed: duration(0),
    });

    // when: a tick happens at 1500
    state = reduce(state, actionTick(instant(1500)));
    // then: 500ms have elapsed
    expect(state).toEqual({
      kind: "running",
      laps: [],
      startTimestamp: instant(1000),
      lastLapTimestamp: instant(1000),
      elapsed: duration(500),
    });

    // when: a lap happens at 2000
    state = reduce(state, actionLap(instant(2000)));
    // then: there is a lap with 1000 duration
    expect(state).toEqual({
      kind: "running",
      laps: [duration(1000)],
      startTimestamp: instant(1000),
      lastLapTimestamp: instant(2000),
      elapsed: duration(500),
    });

    // when: a tick happens at 2500
    state = reduce(state, actionTick(instant(2500)));
    // then: elapsed is 1500 ms
    expect(state).toEqual({
      kind: "running",
      laps: [duration(1000)],
      startTimestamp: instant(1000),
      lastLapTimestamp: instant(2000),
      elapsed: duration(1500),
    });

    // when: a lap happens at 3500
    state = reduce(state, actionLap(instant(3500)));
    // then: there are two laps: 1000 and 1500
    expect(state).toEqual({
      kind: "running",
      laps: [duration(1000), duration(1500)],
      startTimestamp: instant(1000),
      lastLapTimestamp: instant(3500),
      elapsed: duration(1500),
    });

    // when: a stop happens at 4000
    state = reduce(state, actionStop(instant(4000)));
    // then: elapsed is 3000
    expect(state).toEqual({
      kind: "stopped",
      laps: [duration(1000), duration(1500), duration(500)],
      elapsed: duration(3000),
    });

    // when: a reset happens
    state = reduce(state, actionReset());
    // then: state is initial
    expect(state).toEqual({ kind: "initial" });
  });
});
