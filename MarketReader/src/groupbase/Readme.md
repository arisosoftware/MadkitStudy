https://jishuin.proginn.com/p/6335.html
案例简介
本次我们的案例是武汉疫情的SIR简易模型，我们采用的方法是系统动力学。本次案例的效果如下：

在本例中，我们简单的把疫情的几个模块分为

易感染者
感染者
移出者（包括死亡者和治愈者）
参数有：

总人口=5000
接触人数=10
传染概率=0.1
疾病周期=10
在最初，我们认为

易感染者=总人口-1（初始只有一个感染者）
感染者=（初始为1）
移出者=（初始为0）
这个过程中，我们认为：

感染者的增加规律为（接触人数感染者/总人口传染概率*易感染者）
移出者的增加规律为（感染者/疾病周期）


===========
https://github.com/matthiasstein/SystemDynamics-Framework
This Framework allows you to create your own System Dynamics model of complex systems and to simulate its dynamic behavior.


https://dragoon.asu.edu/
https://raw.githubusercontent.com/Dragoon-Lab/LaitsV3/master/www/problems/100.json

http://mansour.saber.free.fr/termadkit/site/madkit/doc/addons/Turtlekit/turtlekit.html












============================
Aivika是一个离散事件模拟（DES）框架，支持面向活动，面向事件和面向流程的范例。 它支持资源抢占和其他改进的仿真技术。 还部分支持系统动力学和基于代理的建模。 所有复杂性都隐藏在易于使用的计算中。
https://github.com/dsorokin/aivika



/**
 * 这是关于排队系统的简单模拟，业务模型如下：
 * 售票窗口系统：剧院由一名售票员负责在窗口售票并同时接待电话咨询服务，窗口服务比
 * 电话服务优先。问讯电话可以由电话存储系统按先后顺序存储（最多可以存5条线路）并
 * 由售票员一一答复。建模的目的是研究售票员的忙闲情况，顾客平均购票时间和平均排队
 * 时间。到达时间和服务时间等可以作为模型参数调整。
 */
 https://github.com/jhsrcmh/MountainMoon/blob/master/src/com/twins/simulate/Simulator.java
 
 
 ===
 /**
 奥雷斯特
Forrester是Systems Dynamics仿真引擎的Java实现。 系统动力学可用于对具有反馈回路的复杂系统进行建模（例如，匆忙在短期内提高生产率，但由于错误而在长期内降低生产率），显着的延迟，非线性相互作用等。

在生态，项目管理，软件开发，业务战略和运营，医学/生物学，政治和国际关系等方面已经开发了许多模型。

系统动力学模型通常用于培训，有时被构造为团队竞争的游戏。

该应用程序将成为某些特定模型和仪表板的基础，这些模型和仪表板可用于培训，计划和/或场景测试。
**/
https://github.com/lwhite1/forrester
