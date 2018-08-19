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

% all values
AllValues_learning = AllValues(1:end, 1:size_LearningProblems);
AllValues_cross = AllValues(1:end, size_LearningProblems+1:end);

% names
Names_learning = raw_Names(1:size_LearningProblems);
Names_cross = raw_Names(size_LearningProblems+1:end);

% learned values
LearnedValues_learning = raw_LearnedValues(1:size_LearningProblems);
LearnedValues_cross = raw_LearnedValues(size_LearningProblems+1:end);

% separators
AllValues_separator = nan(size_RandomResults, 1);
LearnedValues_separatorLeftSide(1:1, 1:size_LearningProblems + 1) = NaN;
LearnedValues_separatorRightSide(1:1, 1:size_CrossValidationProblems) = NaN;

% prepare to display
AllValues_display = [AllValues_learning, AllValues_separator, AllValues_cross];
Names_display = [Names_learning, " ", Names_cross];
LearnedValues_display = [LearnedValues_learning, LearnedValues_separatorRightSide];
CrossValues_display = [LearnedValues_separatorLeftSide, LearnedValues_cross];

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% GRAPH
% make box plot
figure
boxplot(AllValues_display, 'labels', cellstr(Names_display), 'labelorientation', 'inline')

% set new limit for lowest values
ax = gca;
ax.YLim = [0 ax.YLim(2)];

% add markers
hold on
plot(LearnedValues_display, 'dg')
plot(CrossValues_display, 'bo')
hold off

% display separator
line([size_LearningProblems+1 size_LearningProblems+1], ylim);

% legend
title('Learning datasets on LEFT | Cross Validation datasets on RIGHT')
ylabel('Makespan')
legend('Learned values', 'Cross validation values', 'Location', 'best')
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% TABLE
headlines = ["Minimum", "Average", "Maximum", "Median", "Standard Deviation", "Learned Value"];
data = [raw_MinValues' raw_AvgValues' raw_MaxValues' raw_MedianValues' raw_StddevValues' raw_LearnedValues'];
table = uitable(figure, 'RowName', raw_Names, 'ColumnName', headlines, 'Data', data);
table.Position = [0 0 table.Extent(3) table.Extent(4)];
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

