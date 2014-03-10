//package hinasch.mods.unlsaga.core.event;
//
//import hinasch.mods.unlsaga.Unsaga;
//import hinasch.mods.unlsaga.misc.ability.Ability;
//import hinasch.mods.unlsaga.misc.ability.AbilityRegistry;
//
//import java.util.EnumSet;
//
//import net.minecraft.entity.player.EntityPlayer;
//import cpw.mods.fml.common.ITickHandler;
//import cpw.mods.fml.common.TickType;
//
//public class TickHandlerUnsaga implements ITickHandler{
//
//	protected AbilityRegistry ar = Unsaga.abilityRegistry;
//	@Override
//	public void tickStart(EnumSet<TickType> type, Object... tickData) {
//		if (type.equals(EnumSet.of(TickType.PLAYER))) {
//			onPlayerTick((EntityPlayer) tickData[0]);
//		}
//
//	}
//
//	protected void onPlayerTick(EntityPlayer entityPlayer) {
//
//		Ability.tickPlayerAbilityEvent(entityPlayer,ar);
//
//		
//	}
//
//	@Override
//	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
//		// TODO 自動生成されたメソッド・スタブ
//		
//	}
//
//	@Override
//	public EnumSet<TickType> ticks() {
//		// TODO 自動生成されたメソッド・スタブ
//		return EnumSet.of(TickType.PLAYER,TickType.SERVER);
//	}
//
//	@Override
//	public String getLabel() {
//		// TODO 自動生成されたメソッド・スタブ
//		return "armorTick";
//	}
//
//}
