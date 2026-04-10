package jimpossiblemission.view;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import jimpossiblemission.model.User;

/**
 * Cell to see some information of user profile
 * 
 * @author Filippo Taiuti
 *
 */
public class RankingRenderer extends JLabel implements ListCellRenderer<User>
{
    private static final long serialVersionUID = 1L;

    /**
     * Constructor of rankingRederer
     * 
     */
    public RankingRenderer()
    {
        setOpaque(true);
        BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLUE, 3),
                BorderFactory.createEmptyBorder(0, 40, 0, 0));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Component getListCellRendererComponent(JList<? extends User> users, User user, int index, boolean isSelected,
            boolean cellHasFocus)
    {
        ImageIcon imageIcon = new ImageIcon(getClass().getResource(user.getAvatar()));

        setBackground(users.getBackground());
        setForeground(users.getForeground());
        setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        setFont(users.getFont());
        setIcon(imageIcon);
        setText(((Integer) (index + 1)).toString() + " - " + user.toString());

        return this;

    }

}
