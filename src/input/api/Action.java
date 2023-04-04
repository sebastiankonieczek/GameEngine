package input.api;

import java.awt.event.InputEvent;

public interface Action< E extends InputEvent >
{
   public void handle( E event );
}
