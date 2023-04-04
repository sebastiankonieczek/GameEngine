package collision.api;

import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class CollisionHandlerFactory
{
   private static final String CIRCLE_COLLISION_KEY_Y = "y";
   private static final String CIRCLE_COLLISION_KEY_X = "x";
   private static final String CIRCLE_COLLISION_KEY_RADIUS = "radius";
   private static final String CIRCLE_COLLISION_KEY_COLLISION_CIRCLE = "collisionCircle";
   private static final String CIRCLE_COLLISION_KEY_LEVEL = "level";
   private static final String CIRCLE_COLLISION_KEY_COLLISION_LEVEL = "collisionLevel";

   // /////////////////////////////////////////////////////////////////////////////////////////////////////////

   private static final DocumentBuilderFactory DOCUMENT_BUILDER_FACTORY = DocumentBuilderFactory.newInstance();

   // /////////////////////////////////////////////////////////////////////////////////////////////////////////

   public static final CircleCollisionHandler createCircleCollisionHandler( final String xmlFilePath )
   {
      return CircleCollisionHandler.create( parseCircleColissionXml( xmlFilePath ) );
   }

   // /////////////////////////////////////////////////////////////////////////////////////////////////////////

   private static TreeMap< String, ArrayList< CircleCollisionObject > > parseCircleColissionXml( final String xmlFilePath )
   {
      final TreeMap< String, ArrayList< CircleCollisionObject > > collisionObjectLevels = new TreeMap< String, ArrayList<CircleCollisionObject> >();
      final File collisionLevelsFile = new File( xmlFilePath );
      if( !collisionLevelsFile.exists() && !collisionLevelsFile.isFile() ) {
         throw new InvalidParameterException( "File \"" + xmlFilePath +  "\" does not exist!" );
      }

      try {
         final InputSource inputSource = new InputSource( new FileInputStream( collisionLevelsFile ) );
         final DocumentBuilder documentBuilder = DOCUMENT_BUILDER_FACTORY.newDocumentBuilder();
         final Document document = documentBuilder.parse( inputSource );

         final NodeList collisionLevels = document.getElementsByTagName( CIRCLE_COLLISION_KEY_COLLISION_LEVEL );

         // NEEDS FIX A: check format
         for( int i = 0; i < collisionLevels.getLength(); ++i ) {
            final Node item = collisionLevels.item( i );
            final Node levelItem = item.getAttributes().getNamedItem( CIRCLE_COLLISION_KEY_LEVEL );
            final String level = levelItem.getTextContent();

            final NodeList childNodes = ((Element)item).getElementsByTagName( CIRCLE_COLLISION_KEY_COLLISION_CIRCLE );

            if( !collisionObjectLevels.containsKey( level ) ) {
               collisionObjectLevels.put( level, new ArrayList< CircleCollisionObject >() );
            }

            final ArrayList< CircleCollisionObject > arrayList = collisionObjectLevels.get( level );
            for( int j = 0; j < childNodes.getLength(); ++j ) {
               final Node circleItem = childNodes.item( j );
               final NamedNodeMap attributes = circleItem.getAttributes();

               final String radiusAsString = attributes.getNamedItem( CIRCLE_COLLISION_KEY_RADIUS ).getTextContent();
               final String xAsString = attributes.getNamedItem( CIRCLE_COLLISION_KEY_X ).getTextContent();
               final String yAsString = attributes.getNamedItem( CIRCLE_COLLISION_KEY_Y ).getTextContent();

               arrayList.add( CircleCollisionObject.create( Integer.parseInt( radiusAsString ),
                                                            new Point( Integer.parseInt( xAsString ),
                                                                       Integer.parseInt( yAsString ) ) ) );
            }
         }

      } catch ( final SAXException e ) {
         throw new RuntimeException( e );
      } catch ( final FileNotFoundException e ) {
         throw new RuntimeException( e );
      } catch ( final IOException e ) {
         throw new RuntimeException( e );
      } catch ( final ParserConfigurationException e ) {
         throw new RuntimeException( e );
      }

      return collisionObjectLevels;
   }
}
