% make this workspace shared
if matlab.engine.isEngineShared == false
    matlab.engine.shareEngine
    matlab.engine.engineName
    return
else
    isThisShared = matlab.engine.isEngineShared;
end

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% DATA PREPARATION
% rotate values
AllValues = raw_AllValues';
% pick randoms for learning dataset
AllValues_display = AllValues(1:end, 1:size_LearningProblems);

% prepare values for each dataset
Results_1D = raw_LearnedValues(1:size_LearningProblems);
Results_2D = raw_LearnedValues(size_LearningProblems+1:size_LearningProblems*2);
Results_3D = raw_LearnedValues((size_LearningProblems*2)+1:size_LearningProblems*3);
Results_4D = raw_LearnedValues((size_LearningProblems*3)+1:size_LearningProblems*4);
Results_5D = raw_LearnedValues((size_LearningProblems*4)+1:size_LearningProblems*5);
BestPossible = raw_BestKnownValues(1:size_LearningProblems);

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% GRAPH
% make box plot
figure
boxplot(AllValues_display, 'Notch', 'on', 'labels', cellstr(raw_Names(1:size_LearningProblems)), 'labelorientation', 'inline')
% Disable interpreter
ax = gca;
ax.XAxis.TickLabelInterpreter = 'none';
ax.LineWidth = 2;
% change font size and apply custom grid
set(gca,'XTick',1:size_LearningProblems)
set(gca,'XTickLabel',cellstr(raw_Names(1:size_LearningProblems)),'FontSize',23)
set(gca,'Ytick',0:1000:15000)
grid off
grid on

% set new limit for lowest values
ax.YLim = [min(BestPossible)-400 ax.YLim(2)];

% add markers
hold on
plot(Results_1D, 'bd-', 'MarkerSize',7)
plot(Results_2D, 'g*--')
plot(Results_3D, 'c*--')
plot(Results_4D, 'k*--')
plot(Results_5D, 'm*--')
plot(BestPossible, 'r.-')
hold off

% legend
title('Porównanie wyników dopasowania modelu z danymi walidacyjnymi oraz losowymi.')
ylabel('Makespan')
legend('Najlepiej dopasowane rozwi¹zanie', 'Pierwszy zestaw danych walidacyjnych', 'Drugi zestaw danych walidacyjnych', 'Trzeci zestaw danych walidacyjnych', 'Czwarty zestaw danych walidacyjnych', 'Najlepsze znane rozwi¹zanie', 'Location', 'NorthWest')

% annotation
dim = [.137 .28 .4 .4];
str = ['Czas obliczeñ: ' + calculationTime; 'Dopasowanie: ' +  string(str2num(sprintf('%.4f',strrep(string(fitnessValue), 'Fitness: ', '')))); 'Typ metody dopasowania: ' + string(fitnessType)];
note = annotation('textbox', dim, 'String', str, 'FitBoxToText', 'on', 'BackgroundColor', 'w');
note.FontSize = 21;
% change line width
set(findall(gca, 'Type', 'Line'),'LineWidth',1.2);
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% TABLE
names = {'Problem', 'Minimalna', 'Srednia', 'Maksymalna', 'Mediana', 'Odchylenie', 'Dopasowanie', 'Najlepsza'}
data = [raw_Names', raw_MinValues' round(raw_AvgValues', 2) raw_MaxValues' raw_MedianValues' round(raw_StddevValues', 2) raw_LearnedValues' raw_BestKnownValues'];
T = array2table(data ,'VariableNames', names)
writetable(T, "C:\Users\areks\Documents\data.txt")
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

