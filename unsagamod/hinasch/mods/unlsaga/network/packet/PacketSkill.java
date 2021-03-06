package hinasch.mods.unlsaga.network.packet;

import hinasch.mods.unlsaga.entity.EntityDataUnsaga;
import hinasch.mods.unlsaga.item.armor.ItemAccessory;
import hinasch.mods.unlsaga.item.armor.ItemArmorUnsaga;
import hinasch.mods.unlsaga.misc.ability.AbilityRegistry;
import hinasch.mods.unlsaga.misc.ability.HelperAbility;
import hinasch.mods.unlsaga.misc.ability.IGainAbility;
import hinasch.mods.unlsaga.misc.ability.skill.HelperSkill;
import hinasch.mods.unlsaga.misc.ability.skill.effect.InvokeSkill;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.hinasch.lib.AbstractPacket;
import com.hinasch.lib.XYZPos;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class PacketSkill extends AbstractPacket{

	protected int packetId;
	protected XYZPos pos;
	public static class PACKETID{
		public static int WOODBREAKER = 0x01;
		public static int DEBUG_ABILITY = 0x02;
	}
	
	
	public PacketSkill(){
		
	}
	public PacketSkill(int packetid){
		this.packetId = packetid;
	}
	public PacketSkill(int packetid,XYZPos pos){
		this.packetId = packetid;
		this.pos = pos;
	}
	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		buffer.writeInt(packetId);
		if(this.packetId==PACKETID.WOODBREAKER){
			PacketUtil.XYZPosToPacket(buffer, pos);
		}
		
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		this.packetId = buffer.readInt();
		if(this.packetId==PACKETID.WOODBREAKER){
			this.pos = PacketUtil.bufferToXYZPos(buffer);
		}
	}

	@Override
	public void handleClientSide(EntityPlayer player) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void handleServerSide(EntityPlayer player) {
		if(packetId==PACKETID.WOODBREAKER){
			if(player.getHeldItem()!=null){
				InvokeSkill helper = new InvokeSkill(player.worldObj, player, AbilityRegistry.woodBreakerPhoenix,player.getHeldItem() );
				helper.setUsePoint(pos);
				helper.setPrepared(true);
				helper.doSkill();
			}
			
		}
		if(packetId==PACKETID.DEBUG_ABILITY){
		
			if(!player.capabilities.isCreativeMode)return;
			if(player.isSneaking()){
				if(!EntityDataUnsaga.entitySpawnableOnDebug.isEmpty()){
					int size = EntityDataUnsaga.entitySpawnableOnDebug.size();
					int num = size==1 ? 0 : player.getRNG().nextInt(size);
					try {
						Constructor cons = EntityDataUnsaga.entitySpawnableOnDebug.get(num).getConstructor(World.class);
						Entity entity = (Entity) cons.newInstance(player.worldObj);
						entity.setPosition(player.posX, player.posY, player.posZ);
						player.worldObj.spawnEntityInWorld(entity);
					} catch (SecurityException e) {
						// TODO 自動生成された catch ブロック
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						// TODO 自動生成された catch ブロック
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						// TODO 自動生成された catch ブロック
						e.printStackTrace();
					} catch (InstantiationException e) {
						// TODO 自動生成された catch ブロック
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO 自動生成された catch ブロック
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO 自動生成された catch ブロック
						e.printStackTrace();
					}
				}
			}else{
				if(player.getHeldItem()!=null){
					if(player.getHeldItem().getItem() instanceof IGainAbility){
						ItemStack is = player.getHeldItem();
						HelperAbility helper = new HelperAbility(is,(EntityPlayer)player);


						if(is.getItem() instanceof ItemAccessory || is.getItem() instanceof ItemArmorUnsaga){
							if(helper.canGainAbility(is)){
								helper.gainSomeAbility(player.getRNG());
							}else{
								helper.forgetSomeAbility(player.getRNG());
								helper.gainSomeAbility(player.getRNG());
							}

						}else{
							HelperSkill shelper = new HelperSkill(is,player);
							shelper.forgetSomeAbility(player.getRNG());
							shelper.gainSomeAbility(player.getRNG());
						}

					}
				}
			}
					

		}
	}

}
