import { describe, expect, test, vi } from "vitest";
import { buttons, laps } from "./LapTimer";
import { type State, initialState } from "./reducer";
import { duration, instant } from "./time";

const runningState: State = {
  kind: "running",
  laps: [duration(1000), duration(1500)],
  startTimestamp: instant(1000),
  lastLapTimestamp: instant(3500),
  elapsed: duration(3000),
};

const stoppedState: State = {
  kind: "stopped",
  laps: [duration(1000), duration(1500), duration(500)],
  elapsed: duration(3000),
};

describe("laps", () => {
  test("returns nothing on the initial state", () => {
    expect(laps(initialState)).toBeNull();
  });

  test("returns a list with one item per lap on the running state", () => {
    expect(laps(runningState)).toEqual(
      <ul>
        <li key={0}>
          {"Lap 1"} = {"00:00:01.000"}
        </li>
        <li key={1}>
          {"Lap 2"} = {"00:00:01.500"}
        </li>
      </ul>,
    );
  });

  test("returns a list with one item per lap on the stopped state", () => {
    expect(laps(stoppedState)).toEqual(
      <ul>
        <li key={0}>
          {"Lap 1"} = {"00:00:01.000"}
        </li>
        <li key={1}>
          {"Lap 2"} = {"00:00:01.500"}
        </li>
        <li key={2}>
          {"Lap 3"} = {"00:00:00.500"}
        </li>
      </ul>,
    );
  });
});

describe("buttons", () => {
  // The handlers are new functions on each call, so they can't be compared
  // by reference. `expect.any(Function)` only checks that there is a handler.
  const anyHandler = expect.any(Function);

  test("returns only start on the initial state", () => {
    expect(buttons(initialState, vi.fn(), { current: null })).toEqual(
      <button onClick={anyHandler}>start</button>,
    );
  });

  test("returns lap and stop on the running state", () => {
    expect(buttons(runningState, vi.fn(), { current: null })).toEqual(
      <>
        <button onClick={anyHandler}>lap</button>
        <button onClick={anyHandler}>stop</button>
      </>,
    );
  });

  test("returns only reset on the stopped state", () => {
    expect(buttons(stoppedState, vi.fn(), { current: null })).toEqual(
      <>
        <button onClick={anyHandler}>reset</button>
      </>,
    );
  });
});
