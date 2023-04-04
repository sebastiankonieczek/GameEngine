package graphics.api;

import registry.api.Registry;

public class GraphicsObjectRegistry extends Registry< GraphicsObject >
{
   private static final GraphicsObjectRegistry SINGLETON = new GraphicsObjectRegistry();

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   private GraphicsObjectRegistry()
   {
      super();
   }

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   public static final GraphicsObjectRegistry getInstance()
   {
      return SINGLETON;
   }
}
