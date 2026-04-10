package jimpossiblemission.view.gameobject;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import jimpossiblemission.model.entity.SearchableObject;

/**
 * SearchableObject View
 * 
 * @author Filippo Taiuti
 *
 */
public class SearchableObjectView extends StaticObjectView
{
    private int currentValue;
    private double percVisible = 0.0;

    private BufferedImage badgeImage;
    private String pathImage;
    private SearchableObject searchable;
    private int framesAfterDeactivate;

    /**
     * Constructor of SearchableObkject View
     * 
     * @param gameObject as SearchableObject
     */
    public SearchableObjectView(SearchableObject gameObject)
    {
        super(gameObject, "hitboxes.csv", "/Sprites/Objects/Searchable/" + gameObject.getType() + "/");
        pathImage = "/Sprites/Objects/Badge/" + gameObject.getBadge().getBadgeType().toString().toLowerCase()
                + ".png";
        framesAfterDeactivate = 180;
        searchable = gameObject;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isActive()
    {
        if (super.isActive())
            return super.isActive();
        if (!super.isActive() && framesAfterDeactivate <= 0)
            return false;
        framesAfterDeactivate--;
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(Graphics2D g2)
    {
        super.draw(g2);

        // get coordinate for bar and badge
        int middleX = gameObject.getX() + (width / 2);
        int bottomY = gameObject.getY() + height;
        if ((percVisible > 0 && searchable.getSearchPercentage() < 100)
                || searchable.getSearchPercentage() != currentValue)
        {
            percVisible -= 0.2;
            if (searchable.getSearchPercentage() != currentValue)
            {
                currentValue = searchable.getSearchPercentage();
                percVisible = 1.0;
            }

            // bar dimension
            int barWidth = 40;
            int barHeight = 8;
            // bar in the middle
            int barX = middleX - (barWidth / 2);
            int barY = bottomY - 45;

            // --- DRAW ---
            // Background
            g2.setColor(Color.BLACK);
            g2.fillRect(barX, barY, barWidth, barHeight);

            // Filled part
            g2.setColor(Color.GREEN);
            int progressWidth = (currentValue * barWidth) / 100;
            g2.fillRect(barX, barY, progressWidth, barHeight);

            // Border color
            g2.setColor(Color.WHITE);
            g2.drawRect(barX, barY, barWidth, barHeight);

            // Percentage (same color of filled part)
            g2.setColor(Color.GREEN);
            g2.setFont(new Font("Monospaced", Font.BOLD, 10));

            g2.drawString(currentValue + "%", barX + barWidth + 5, barY + barHeight);
        }
        if (searchable.getSearchPercentage() == 100)
        {
            try
            {
                badgeImage = ImageIO.read(this.getClass().getResourceAsStream(pathImage));
            } catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            g2.drawImage(badgeImage, middleX - 32, bottomY - 55, 64, 32, null, null);
        }
    }

}
