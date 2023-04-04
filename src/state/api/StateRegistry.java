package state.api;

import registry.api.Registry;

public class StateRegistry extends Registry< State >
{
   private static final StateRegistry               SINGLETON     = new StateRegistry();

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   private StateRegistry()
   {
      super();
   }

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   public static final StateRegistry getInstance()
   {
      return SINGLETON;
   }
}
