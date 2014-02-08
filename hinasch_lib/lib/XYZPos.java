package hinasch.lib;

import net.minecraft.entity.Entity;


public class XYZPos {

	public int x;
	public int y;
	public int z;
	public boolean sw = false;
	
	public double dx;
	public double dy;
	public double dz;
	
	public XYZPos(int par1,int par2,int par3){
		x=par1;
		y=par2;
		z=par3;
		dx=(double)par1;
		dy=(double)par2;
		dz=(double)par3;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		
		sb.append(x).append(",").append(y).append(",").append(z);
		return new String(sb);
		
	}
	
	
	public static XYZPos strapOff(String par1){
		String[] str = par1.split(",");
		if(str.length<3)return null;
		XYZPos xyz = new XYZPos(Integer.parseInt(str[0]),Integer.parseInt(str[1]),Integer.parseInt(str[2]));
		return xyz;
		
	}
	
	public static XYZPos entityPosToXYZ(Entity en){
		XYZPos xyz = new XYZPos((int)en.posX,(int)en.posY,(int)en.posZ);
		xyz.dx = en.posX;
		xyz.dy = en.posY;
		xyz.dz = en.posZ;
		return xyz;
	}
	
	public static XYZPos[] swap(XYZPos par1,XYZPos par2){
		XYZPos[] xyz = new XYZPos[2];
		XYZPos newxyz = new XYZPos(0,0,0);
		XYZPos newxyze = new XYZPos(0,0,0);
		if(par1.x>par2.x){
			newxyz.x = par2.x;
			newxyze.x = par1.x;
		}else{
			newxyz.x = par1.x;
			newxyze.x = par2.x;
		}
		if(par1.y>par2.y){
			newxyz.y = par2.y;
			newxyze.y = par1.y;
		}else{
			newxyz.y = par1.y;
			newxyze.y = par2.y;
		}
		if(par1.z>par2.z){
			newxyz.z = par2.z;
			newxyze.z = par1.z;
		}else{
			newxyz.z = par1.z;
			newxyze.z = par2.z;
		}
		xyz[0] = newxyz;
		xyz[1] = newxyze;
		return xyz;
		
	}
	
	public XYZPos subtract(XYZPos par2){
		return new XYZPos(this.x - par2.x,this.y - par2.y,this.z - par2.z);
	}
	
	public XYZPos add(XYZPos par2){
		return new XYZPos(this.x + par2.x,this.y + par2.y,this.z + par2.z);
	}
}
