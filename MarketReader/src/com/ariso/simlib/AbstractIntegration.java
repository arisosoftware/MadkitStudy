package com.ariso.simlib;

import java.util.List;

import com.ariso.simlib.model.Converter;
import com.ariso.simlib.model.Nodes;

/**
 * Abstract class that describes an integration method.
 *
 * @author <a href="mailto:arisosoftware@gmail.com">Ariso software ltd</a>
 */
public abstract class AbstractIntegration {

	private double dt;
	private List<Nodes> nodeList;
	private List<Converter> variableConverter;

	protected List<Nodes> getNodeList() {
		return nodeList;
	}

	public void setNodeList(List<Nodes> node) {
		this.nodeList = node;
	}

	protected List<Converter> getVariableConverter() {
		return variableConverter;
	}

	public void setVariableConverter(List<Converter> variableConverter) {
		this.variableConverter = variableConverter;
	}

	protected double getDt() {
		return dt;
	}

	public void setDt(double dt) {
		this.dt = dt;
	}

	/**
	 * integrate interface method
	 **/
	public abstract void integrate();

}

/*
1、抽象类和接口都不能直接实例化。如果要实例化，涉及到多态。抽象类要实例化，抽象类定义的变量必须指向一个子类变量，这个子类继承并实现了抽象类所有的抽象方法。接口要实例化，接口定义的变量必须指向一个子类变量，这个子类继承并实现了接口所有的方法。
2、抽象要被子类继承，接口要被子类实现。
3、接口里只能对方法进行声明，抽象类里既可以对方法进行声明，又可以实现。
4、抽象类里面的抽象方法必须被子类实现，如果子类不能全部实现，子类必须也是抽象类。接口里面的方法必须被子类实现，如果子类不能全部实现，子类必须是抽象类。
5、接口里面的方法不能有具体的实现，这说明接口是设计的结果，而抽象类是重构的结果。
6、抽象类里面可以没有抽象方法，如果一个类里面有抽象方法，那么这个类一定是抽象类。
7、抽象类中的抽象方法都要被实现，所以抽象方法不能是静态的static，也不能是私有的private。
8、接口可以继承接口，甚至可以继承多个接口；类可以实现多个接口，只能继承一个类。
9、抽象类主要用来抽象类别，接口主要用来抽象方法功能。关注事物的本质，用抽象类；关注一种操作，用接口。
————————————————
版权声明：本文为CSDN博主「frankfan123」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/frankfan123/java/article/details/79628034
*/

