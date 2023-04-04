package registry.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Registry< E >
{
   private static final String DEFAULT_GROUP = "DEFAULT";

   private final Map< String, ArrayList< E >> values_;

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   protected Registry()
   {
      values_ = new HashMap< String, ArrayList<E> >();
      values_.put( DEFAULT_GROUP, new ArrayList< E >() );
   }

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   public synchronized void registerValue( final E value )
   {
      values_.get( DEFAULT_GROUP ).add( value );
   }

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   public synchronized void registerValueForGroup( final String group, final E value )
   {
      if( !values_.containsKey( group ) ) {
         values_.put( group, new ArrayList< E >() );
      }
      values_.get( group ).add( value );
   }

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   public synchronized List< E > getValues()
   {
      final ArrayList< E > allStates = new ArrayList< E >();
      for( final ArrayList< E > value : values_.values() ) {
         allStates.addAll( (ArrayList< E >)value.clone() );
      }
      return allStates;
   }

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   public synchronized List< E > getValuesForGroup( final String group )
   {
      if( !values_.containsKey( group ) ) {
         return new ArrayList< E >();
      }

      return (ArrayList< E >)values_.get( group ).clone();
   }

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   public synchronized void removeGroup( final String group )
   {
      values_.remove( group );
   }

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   public synchronized void removeValue( final E value )
   {
      for( final ArrayList< E > values : values_.values() ) {
         if( values.remove( value ) ) {
            return;
         }
      }
   }

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   public synchronized void removeValueFromGroup( final String group, final E value )
   {
      if( values_.containsKey( group ) ) {
         values_.get( group ).remove( value );
      }
   }

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   public synchronized void clearRegistry()
   {
      values_.clear();
   }

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   protected Map< String, ArrayList< E > > values()
   {
      return values_;
   }
}
