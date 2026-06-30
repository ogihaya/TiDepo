import { describe, expect, it } from 'vitest';
import { greeting } from './greeting';

describe('greeting', () => {
  it('returns a greeting message', () => {
    expect(greeting('TiDepo')).toBe('Hello, TiDepo!');
  });
});
