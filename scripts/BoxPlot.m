% make this workspace shared
if matlab.engine.isEngineShared == false
    matlab.engine.shareEngine
    matlab.engine.engineName
else
    isThisShared = matlab.engine.isEngineShared;
end

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% GRAPH
% make box plot
figure
boxplot(values', 'labels', cellstr(names), 'labelorientation', 'inline')

% set new limit for lowest values
ax = gca;
ax.YLim = [0 ax.YLim(2)];

% add markers
hold on
plot(learned, 'dg')
plot(stddev, '+b')
hold off

% legend
title('Random makespan values versus learned ones')
ylabel('Calculated value')
legend('Learned Values','Standard Deviation' , 'Location', 'best')
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% TABLE
headlines = ["Minimum", "Average", "Maximum", "Median", "Standard Deviation", "Learned Value"];
data = [min' avg' max' median' stddev' learned'];
table = uitable(figure, 'RowName', names, 'ColumnName', headlines, 'Data', data);
table.Position = [0 0 table.Extent(3) table.Extent(4)];
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

