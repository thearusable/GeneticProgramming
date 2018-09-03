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
AllValues_display = AllValues(1:end, 1:size_LearningProblems-1);

% prepare values for each dataset
Results_1D = raw_LearnedValues(1:size_LearningProblems);
Results_2D = raw_LearnedValues(size_LearningProblems+1:size_LearningProblems*2);
Results_3D = raw_LearnedValues((size_LearningProblems*2)+1:size_LearningProblems*3);
Results_4D = raw_LearnedValues((size_LearningProblems*3)+1:size_LearningProblems*4);
Results_5D = raw_LearnedValues((size_LearningProblems*4)+1:size_LearningProblems*5);

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% GRAPH
% make box plot
figure
boxplot(AllValues_display, 'labels', cellstr(Names_learning(1:7)), 'labelorientation', 'inline')

% set new limit for lowest values
ax = gca;
ax.YLim = [min(Results_1D)-150 ax.YLim(2)];

% add markers
hold on
plot(Results_1D, 'b+-')
plot(Results_2D, 'g+-')
plot(Results_3D, 'c+-')
plot(Results_4D, 'k+-')
plot(Results_5D, 'm+-')
hold off

% legend
title('Learning and Cross Validation values compared with randoms')
ylabel('Makespan')
legend('Learned values', 'First Cross Validation Dataset', 'Second Cross Validation Dataset', 'Third Cross Validation Dataset', 'Fourth Cross Validation Dataset', 'Location', 'best')
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% TABLE
headlines = ["Minimum", "Average", "Maximum", "Median", "Standard Deviation", "Learned Value"];
data = [raw_MinValues' raw_AvgValues' raw_MaxValues' raw_MedianValues' raw_StddevValues' raw_LearnedValues'];
table = uitable(figure, 'RowName', raw_Names, 'ColumnName', headlines, 'Data', data);
table.Position = [0 0 table.Extent(3) table.Extent(4)];
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

