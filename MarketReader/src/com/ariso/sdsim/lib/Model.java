package com.ariso.sdsim.lib;

import java.util.HashMap;
import java.util.ArrayList;
import java.text.DecimalFormat;
import java.util.List;

import com.ariso.simlib.AbstractIntegration;
import com.ariso.simlib.AbstractModelEntity;
import com.ariso.simlib.AppConfig;
import com.ariso.simlib.func.EulerCauchyIntegration;

/**
 * This class represents a simulation model. It defines all
 * {@link AbstractModelEntity} instances and their cause-effect relationships.
 *
 * @author <a href="mailto:arisosoftware@gmail.com">Ariso software ltd</a>
 */
public class Model {

	HashMap<String, Entity> entityMap;
	HashMap<String, AbstractFlow> flowMap;

	public int currentStep;
	public int totalSteps;

	public Model() {
		this.entityMap = new HashMap<String, Entity>();
		this.flowMap = new HashMap<String, AbstractFlow>();
	}

	public Entity getEntity(String entityName) {
		return entityMap.get(entityName);
	}

	public AbstractFlow getFlow(String flowName) {
		return flowMap.get(flowName);
	}

	public void addEntity(Entity entity) throws Exception {
		if (!this.entityMap.containsKey(entity.label)) {
			this.entityMap.put(entity.label, entity);
		} else {
			throw new Exception(AppConfig.DUPLICATE_MODEL_ENTITY_EXCEPTION);
		}
	}

	public void addFlow(AbstractFlow flow) throws Exception {
		if (!this.flowMap.containsKey(flow.label)) {
			this.flowMap.put(flow.label, flow);
			flow.model = this;
		} else {
			throw new Exception(AppConfig.DUPLICATE_FLOW_EXCEPTION);
		}
	}

	public void addEnityFlow(String EntityName, String FlowName) {
		entityMap.get(EntityName).flow.add(this.flowMap.get(FlowName));
	}

//	public void linkDataFlow(String source, String target, String flow) throws Exception {
//		Entity sourceEntity = this.entityMap.get(source);
//		Entity targetEntity = this.entityMap.get(target);
//		AbstractFlow flowAbstractFlow =  this.flowMap.get(flow);
//		
//		
//		
//		
//	//	flowAbstractFlow.inputEntityMapParams.put(sourceEntity.label,sourceEntity);		
//	//	sourceEntity.outgoingFlow.add(flowAbstractFlow);
//	//	targetEntity.incomingFlow.add(flowAbstractFlow);
//		
//		
//	}
//
//	public void linkDataFlow(Entity source, Entity target, AbstractFlow flow) throws Exception {
//	//	source.outgoingFlow.add(flow);
//	//	target.incomingFlow.add(flow);
//	}

	public List<String> reportModelEntitiesValues() {
		ArrayList<Entity> modelEntities = new ArrayList<Entity>(this.entityMap.values());
		ArrayList<String> modelEntitiesValues = new ArrayList<String>();

		modelEntities.forEach((modelEntity) -> {
			modelEntitiesValues.add(new DecimalFormat("#.######").format(modelEntity.GetValue()));
		});

		return modelEntitiesValues;
	}

	public void reportConsole() {
		System.out.println("reportConsole");
		this.entityMap.values().forEach((modelEntity) -> {
			System.out.println(modelEntity.label + ":" + new DecimalFormat("#.######").format(modelEntity.GetValue()));
		});
	}

	public void GetReport() {
		StringBuilder sb = new StringBuilder();

		if (this.currentStep == 0) {
			sb.append("<tr><th>step</th>");
//			sb.append("Step\t");
//			this.entityMap.values().forEach((entity) -> {
//				sb.append(entity.label);
//				sb.append("\t\t");
//			});
//			sb.append("\t");
//			this.flowMap.values().forEach((flowrow) -> {
//				sb.append(flowrow.label);
//				sb.append("\t");				
//			});
//			sb.append("\n");

			this.entityMap.values().forEach((entity) -> {
				sb.append("<th>").append(entity.label).append("</th>");
			});
			sb.append("<th></th>");
			this.flowMap.values().forEach((flowrow) -> {
				sb.append("<th>").append(flowrow.label).append("</th>");
				sb.append(flowrow.label);

			});
			sb.append("</tr>");
			sb.append("\n");

		}
		sb.append("<tr>");
		sb.append("<td>").append(this.currentStep).append("</td>");

		this.entityMap.values().forEach((entity) -> {
			sb.append("<td>").append(String.format("%5.2f", entity.GetValue())).append("</td>");

		});
		sb.append("<td>").append("</td>");
		this.flowMap.values().forEach((flowrow) -> {
			sb.append("<td>").append(String.format("%5.2f",flowrow.value)).append("</td>");
		});
		sb.append("</tr>\n");

		System.out.print(sb.toString());
	}

	public void go_step() {

		// step 1, calc all data flow values
		this.flowMap.values().forEach((flowrow) -> {
			flowrow.compute();

		});

		// step 2, calc all entity
		this.entityMap.values().forEach((entity) -> {
			entity.applyFlowValues();
		});

		GetReport();
		this.currentStep++;
	}

	public void run() {
		System.out.println("<head>\n" + 
				"<style>\n" + 
				"table, th, td {\n" + 
				"  border: 1px solid black;\n" + 
				"}table {\n" + 
				"  border-collapse: collapse;\n" + 
				"}\n" + 
				"th, td {\n" + 
				" padding: 1px 15px;\n" + 
				"  text-align: right;\n" + 
				"}</style>\n" + 
				"</head>"
				+ "\n<table>");
		for (int i = 0; i < this.totalSteps; i++) {
			go_step();
		}
		System.out.println("\n</table>");
	}

	public void EntityAddFlow(String entityName, String flowName) {
		Entity entity = this.getEntity(entityName);
		AbstractFlow flow = this.getFlow(flowName);
		entity.flow.add(flow);

	}

//	public void EntityAddFlow(Entity entity, AbstractFlow flow) {
//		 
//	}

}
