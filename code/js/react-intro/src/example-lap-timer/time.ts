// Branded types for both instants and durations
// See https://www.learningtypescript.com/articles/branded-types
export type Instant = number & { readonly __brand: "Instant" };

// An amount of time, in milliseconds.
export type Duration = number & { readonly __brand: "Duration" };

export function instant(ms: number): Instant {
  // oxlint-disable-next-line typescript/no-unsafe-type-assertion -- the only place where an Instant is created
  return ms as Instant;
}

export function duration(ms: number): Duration {
  // oxlint-disable-next-line typescript/no-unsafe-type-assertion -- the only place where a Duration is created
  return ms as Duration;
}

export function now(): Instant {
  return instant(performance.now());
}

export function durationBetween(end: Instant, start: Instant): Duration {
  return duration(end - start);
}

// Formats as hh:mm:ss.mmm, e.g. 83456 -> "00:01:23.456"
export function formatDuration(d: Duration): string {
  // performance.now() has sub-millisecond precision
  const ms = Math.floor(d);
  const h = Math.floor(ms / 3600000);
  const min = Math.floor(ms / 60000) % 60;
  const sec = Math.floor(ms / 1000) % 60;
  return `${pad(h)}:${pad(min)}:${pad(sec)}.${pad(ms % 1000, 3)}`;
}

function pad(n: number, len: number = 2): string {
  return String(n).padStart(len, "0");
}
