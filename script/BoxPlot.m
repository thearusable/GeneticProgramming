% make this workspace shared
if matlab.engine.isEngineShared == false
    matlab.engine.shareEngine
    matlab.engine.engineName
else
    isThisShared = matlab.engine.isEngineShared;
end

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% DATA PREPARATION
% rotate values
rotatedValues = values';

% slice data
valuesFromLearning = rotatedValues(1:end,1:learningProblems);
namesFromLearning = names(1:learningProblems);
learnedLeft = learned(1:learningProblems);
valuesSeparator = nan(1000000,1);
valuesFromCross = rotatedValues(1:end,learningProblems+1:end);
namesFromCross = names(learningProblems+1:end);
learnedRight = learned(learningProblems+1:end);

% join
valuesToDisplay = [valuesFromLearning, valuesSeparator, valuesFromCross];
namesToDisplay = [namesFromLearning, " ", namesFromCross];
learnedToDisplay = [learnedLeft, NaN, learnedRight];

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% GRAPH
% make box plot
figure
boxplot(valuesToDisplay, 'labels', cellstr(namesToDisplay), 'labelorientation', 'inline')

% set new limit for lowest values
ax = gca;
ax.YLim = [0 ax.YLim(2)];

% add markers
hold on
plot(learnedToDisplay, 'dg')
hold off

% display separator
line([learningProblems+1 learningProblems+1], ylim);

% legend
title('Learning datasets on LEFT | Cross Validation datasets on RIGHT')
ylabel('Calculated value')
legend('Cross validation value', 'Location', 'best')
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% TABLE
headlines = ["Minimum", "Average", "Maximum", "Median", "Standard Deviation", "Learned Value"];
data = [min' avg' max' median' stddev' learned'];
table = uitable(figure, 'RowName', names, 'ColumnName', headlines, 'Data', data);
table.Position = [0 0 table.Extent(3) table.Extent(4)];
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

