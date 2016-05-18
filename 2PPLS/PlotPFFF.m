function PlotPFFF()
ss=[];
minf1=[];
minf2=[];
mminf1=[];
mminf2=[];

for i=0:29
% name1=[int2str(i),'randomy.txt'];
 name2=[int2str(i),'PEy.txt'];
 name10=['3-100\']
% eval(['load 实验结果\MOMAD\',name10,name1]);
% eval(['load 实验结果\MOMAD\',name10,name2]);

% load 1randomy.txt;
% load 1PEy.txt;
% load 0PLy.txt;
% load 0randomPF.txt;

% % % % % % name31=['X',int2str(i),'randomy','(:,1),'];
% % % % % % name32=['X',int2str(i),'randomy','(:,2),'];
% % % % % % gg=['g^'];
% % % % % % eval(['plot','(',name31,name32,'gg',')']);
% % % % % % %plot(X1randomy(:,1),X1randomy(:,2),'g*');
% % % % % % hold on
% plot(randomPF(:,1),randomPF(:,2),'bo');
% hold on
% name41=['X',int2str(i),'PEy','(:,1),'];
% name42=['X',int2str(i),'PEy','(:,2),'];
% gg=['r*'];
% eval(['plot','(',name41,name42,'gg',')']);
% hold on 

eval(['load 2PPLS\',name10,name2]);
name51=['X',int2str(i),'PEy','(:,1),'];
name52=['X',int2str(i),'PEy','(:,2),'];
gg=['bo'];
eval(['plot','(',name51,name52,'gg',')']);
hold on 


 name5=['X',int2str(i),'PEy'];
% name6=['X',int2str(i),'randomy'];


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%NPS
% name6=[int2str(i),'NPSy.txt'];
% eval(['load 实验结果\NPS\',name10,name6]);
% name61=['X',int2str(i),'NPSy','(:,1),'];
% name62=['X',int2str(i),'NPSy','(:,2),'];
% gg=['go'];
% eval(['plot','(',name61,name62,'gg',')']);
% hold on 

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%NPS
% 
% eval(['load 实验结果\MOMAD\',name10,name2]);
 eval(['a1','=',name5]);
% eval(['load 实验结果\IMOMAD\',name10,name2]);
 eval(['a2','=',name5]);
minf1=[minf1;min(a1(:,1))];
minf2=[minf2;min(a1(:,2))];

mminf1=[mminf1;min(a2(:,1))];
mminf2=[mminf2;min(a2(:,2))];

[h1,l1]=size(a1);
[h2,l2]=size(a2);
m=0;
for ii=1:h2
    for jj=1:h1
   if (a1(jj,1)<=a2(ii,1)&&a1(jj,2)<=a2(ii,2))
    m=m+1;
    break;
   end
    end
end
c=m/h2;
ss=[ss;c];
% name4=['X',int2str(i),'PEy'];
% eval(['plot','(',name4,'(:,1)',',',name4,'(:,2)',',','y*',')']);
%plot(X1PEy(:,1),X1PEy(:,2),'y*');


hold off
end
av=mean(ss)

Aminf1=mean(minf1)
Aminf2=mean(minf2)

Amminf1=mean(mminf1)
Amminf2=mean(mminf2)
%plot(PLy(:,1),PLy(:,2),'yo');
%hold on 
% plot(X12311(:,1),X12311(:,2),'ko');
% hold on 

% load 1.txt;
% load 1231.txt;
% load 12311.txt
% load 000000.txt;

% [m,n]=size(X123);
% [C,ia,ic] = unique(X123,'rows')
% 
% plot(X12(:,1),X12(:,2),'bo');
% hold on
% plot(X1(:,1),X1(:,2),'g*');
% hold on
% plot(C(:,1),C(:,2),'r*');
% hold on 
% plot(X1231(:,1),X1231(:,2),'yo');
% hold on 
% plot(X12311(:,1),X12311(:,2),'ko');
% hold on 
% 
% plot(X000000(:,1),X000000(:,2),'ms')