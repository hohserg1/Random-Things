package lumien.randomthings.handler;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;

import lumien.randomthings.RandomThings;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ItemCatcher
{
	static boolean catchingDrops = false;
	static List<ItemStack> catchedDrops = new ArrayList<>();

	protected static void entityJoinWorld(EntityJoinWorldEvent event)
	{
		if (!event.getWorld().isRemote && event.getEntity() instanceof EntityItem)
		{
			if (catchingDrops && !event.isCanceled())
			{
				catchedDrops.add(((EntityItem) event.getEntity()).getEntityItem());
				event.setCanceled(true);
			}
		}
	}

	public static void startCatching()
	{
		if (catchingDrops)
		{
			RandomThings.instance.logger.log(Level.WARN, "Already catching drops, unexpected!");
		}

		catchingDrops = true;
	}
	
	public static List<ItemStack> stopCatching()
	{
		if (!catchingDrops)
		{
			RandomThings.instance.logger.log(Level.WARN, "Not catching drops, unexpected!");
		}
		
		ArrayList<ItemStack> copy = new ArrayList<ItemStack>(catchedDrops);
		catchedDrops.clear();
		
		catchingDrops = false;
		return copy;
	}

	public static boolean isCatching()
	{
		return catchingDrops;
	}
}
