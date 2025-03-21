package com.davidpe.chessjourney.domain.common;

import com.davidp.chessjourney.domain.common.Col;
import com.davidp.chessjourney.domain.common.Pos;
import com.davidp.chessjourney.domain.common.Row;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;

public class PosTest {

  @Test
  public void posTest() {

    Pos pos1 = Pos.of(Col.E, Row.FOUR);
    assertSame(pos1.getRow(), Row.FOUR);
    assertSame(pos1.getCol(), Col.E);

    Pos pos2 = Pos.parseString("e4");
    assertSame(pos2.getRow(), Row.FOUR);
    assertSame(pos2.getCol(), Col.E);
  }
}
