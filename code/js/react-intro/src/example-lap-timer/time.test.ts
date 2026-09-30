import { describe, expect, test } from "vitest";
import { duration, formatDuration } from "./time";

describe("formatDuration", () => {
  test.each([
    { ms: 0, expected: "00:00:00.000" },
    { ms: 5, expected: "00:00:00.005" },
    { ms: 999.9, expected: "00:00:00.999" }, // fractions are truncated, not rounded
    { ms: 1000, expected: "00:00:01.000" },
    { ms: 83456.7, expected: "00:01:23.456" },
    { ms: 3600000, expected: "01:00:00.000" },
    { ms: 3600000 + 2 * 60000 + 3 * 1000 + 4, expected: "01:02:03.004" },
    { ms: 100 * 3600000, expected: "100:00:00.000" }, // hours are not capped
  ])("formats $ms ms as $expected", ({ ms, expected }) => {
    expect(formatDuration(duration(ms))).toBe(expected);
  });
});
