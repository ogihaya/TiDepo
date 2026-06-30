import { greeting } from '../lib/greeting';

export function AppTitle() {
  return <h1>{greeting('TiDepo')}</h1>;
}
